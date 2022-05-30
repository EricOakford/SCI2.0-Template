;;; Sierra Script 1.0 - (do not remove this comment)
;
;	GAMEEGO.SC
;
;	 GameEgo is a game-specific subclass of Ego. Here, you can put default answers for
;	 looking, talking and performing actions on yourself.
;
;

(script# GAME_EGO)
(include game.sh) (include "7.shm")
(use Main)
(use Grooper)
(use StopWalk)
(use Procs)
(use Invent)
(use Ego)
(use System)

(public
	GameEgo 0
)

(procedure (statCheck statNum statMult)
	(self useSkill: statNum statMult)
	(return (* [egoStats statNum] statMult))
)

(class GameEgo of Ego
	(properties
		noun N_EGO
		modNum GAME_EGO
		view vEgo
		badgeWorn 0
		;EO: will probably replace the wearable capes with badges.
		; This is to ensure that we don't need separate views for each
		; cape being worn.		
	)
	
	(method (doVerb theVerb &tmp n v c m projX projY evt howMuch)
		;doing this QFG3 style
		(= n N_EGO)
		(= v theVerb)
		(= c 0)
		(= m modNum)
		(switch theVerb
			;add interactivity with the player character here
			(V_DO
				(if (== contentLevel contentEXPLICIT)
					(= c C_EXPLICIT)
				else
					(= c C_CLEAN)
				)
			)
			(V_FOOD
				(if (< freeMeals 2)
					(cond
						((Btst fStarving)
							(Bclr fStarving)
						)
						((Btst fHungry)
							(Bclr fHungry)
						)
						(else
							(++ freeMeals)
						)
					)
					(self use: iFood)
				else
					(= c C_NOT_HUNGRY)
				)
			)
			(V_AMULET
				;just to be absolutely sure; maybe we can set this flag
				; immediately upon obtaining the amulet
				(Bset fWearingAmulet)
			)
			(V_HEALING_POTION
				(if (< [egoStats HEALTH] (MaxHealth))
					(self use: iHealingPotion)
					(TakeDamage (- (/ (MaxHealth) 2)))
				else
					(= v 0)
					(= c C_DONT_NEED_POTION)
				)
			)
			(V_VIGOR_POTION
				(if (< [egoStats STAMINA] (MaxStamina))
					(self use: iVigorPotion)
				else
					(= v 0)
					(= c C_DONT_NEED_POTION)				
				)
			)
			(V_MANA_POTION
				(if (< [egoStats MANA] (MaxMana))
					(self use: iManaPotion)
				else
					(= v 0)
					(= c C_DONT_NEED_POTION)
				)
			)
			(V_BEER_KEG
				(if ((inventory at: iBeerKeg) state?)
					(= c C_DRUGGED_BEER)
				)
			)
			(V_FLAME
				(if (self castSpell: FLAMEDART)
					(= projX ((= evt (Event new:)) x?))
					(= projY (+ (evt y?) 25))
					(evt dispose:)
					(CastDart 0 0 projX projY)
				)
			)
			(V_WHISTLE
				;Allow the whistle to work in the dungeon
				(if (== curRoomNum rCastleDungeon)
					(return FALSE)
				)
			)
			(V_HEAL
				(if (< [egoStats HEALTH] (ego maxHealth:))
					(if (self castSpell: HEAL)
						(= c 0)
						(= howMuch
							(Min
								(- (ego maxHealth:) [egoStats HEALTH])
								[egoStats HEAL]
							)
						)
						(+= [egoStats HEALTH] howMuch)
					)
				else
					(= c C_FULL_HEALTH)
				)
			)
		)
		;doing this QFG4 style
		(if (Message MsgSize m n v c 1)
			;we've got a specific message for using an item on ego
			(messager say: n v c 0 0 m)
		else
			;we don't have one, so fall back to a default message
			(messager say: n V_COMBINE NULL 1 0 m)
		)
	)
	
	(method (changeGait newGait gaitMsg &tmp theView saveMover)
		(= saveMover 0)
		(if (and gaitMsg (== egoGait newGait))
			(return))
		(if mover
			(= saveMover mover)
			(= mover 0)
		)
		(if (!= newGait -1)
			(= egoGait newGait)
		)
		(switch egoGait
			(MOVE_RUN
				(self
					view: vEgoRun
					origStep: 2053
					setStep:
					setCycle: egoStopWalk vEgoStand
				)
			)
			(MOVE_SNEAK
				(self
					view: vEgoSneak
					origStep: 1026
					setStep:
					setCycle: egoStopWalk -1
				)
			)
			(else 
				(self
					view: vEgo
					origStep: 1027
					setStep:
					setCycle: egoStopWalk vEgoStand
				)
			)
		)
		(cond 
			((or (== xStep 1) (== yStep 1))
				(if (== view vEgoRun)
					(self setStep: 8 4 0)
				else
					(self setStep: 4 2 0)
				)
			)
		)
		(if saveMover
			(self mover: saveMover)
			((self mover?) init:)
		)
	)

	
	(method (normalize theView)
		;sets up ego's normal animation
		(= view (if argc theView else vEgo))
		(ego
			setLoop: -1
			setPri: -1
			setMotion: 0
			setCycle: egoStopWalk vEgoStand
			z: 0
			illegalBits: 0
			ignoreActors: FALSE
		)
	)

	(method (showInv)
		(if
			;can't bring up the inventory in combat
			(OneOf curRoomNum
				vGoblin vGuard vRogue
			)
			(return)
		)
		(theIconBar hide:)
		(inventory showSelf: ego)
	)
	
	(method (useSkill skillNum learnValue &tmp learnSign)
		(* learnValue 8)
		(if (not [egoStats skillNum])
			(return FALSE)
		)
		(if (== skillNum THROW)
			(self useStamina: 1)
		)
		(= learnSign (if (>= learnValue 0) 1 else -1))
		(if (> (Abs learnValue) [egoStats skillNum])
			(= learnValue (* [egoStats skillNum] learnSign))
		)
		(+= [egoStats EXPER] (/ (+ (Abs learnValue) 19) 20))
		(+= [skillTicks skillNum] learnValue)
		(cond
			((>= [skillTicks skillNum] [egoStats skillNum])	;
				(-= [skillTicks skillNum] [egoStats skillNum])
				(if (> (+= [egoStats skillNum] (Random 2 4)) 400)
					(= [egoStats skillNum] 400)	;stats max out at 400
				)
			)
			((< [skillTicks skillNum] 0)	;losing the skill
				(+= [skillTicks skillNum] [egoStats skillNum])
				(if (< (-= [egoStats skillNum] (Random 2 4)) 5)
					(= [egoStats skillNum] 5)	;can't go lower than 5
				)
			)
			(else	;no change yet
				(return FALSE)
			)
		)
	)

	(method (trySkill skillNum difficulty bonus &tmp skVal skDiv skRef success)
		(= success TRUE)
		(if (= skVal [egoStats skillNum])
			(if (== argc 3) (+= skVal bonus))
			(if difficulty
				(if (and (< skillNum MAGIC) (!= skillNum INT) (!= skillNum LUCK))
					(self useStamina: (/ (+ difficulty 39) 40))
				)
			else
				(if (and (>= skillNum WEAPON) (< skillNum MAGIC))
					(self useStamina: (Random 2 8))
				)
				(= difficulty (Rand300))
			)
			(if (>= (statCheck LUCK 1) (Random 1 500))
				(+= skVal (Random 1 20))
			)
			(if (<= difficulty skVal)
				(= success TRUE)
			else
				(= success -1)
			)
			(if (= skDiv 2)
				(cond 
					((== skillNum WEAPON)
						(self useSkill: STR 3)
						(self useSkill: AGIL 2)
					)
					((or (== skillNum PARRY) (== skillNum DODGE) (== skillNum STEALTH))
						(self useSkill: AGIL 4)
						(self useSkill: INT 2)
					)
					((== skillNum PICK)
						(self useSkill: AGIL 8)
						(self useSkill: INT 5)
					)
					((or (== skillNum THROW) (== skillNum CLIMB))
						(self useSkill: STR 5)
						(self useSkill: AGIL 4)
					)
					((>= skillNum OPEN)
						(self useSkill: MAGIC 8)
						(self useSkill: INT 4)
					)
				)
				(= skRef 100)
				(if (or (< skillNum MAGIC) (== skillNum WEAPON))
					(= skRef 25)
				)
				(self useSkill: skillNum (Abs (/ skRef skDiv)))
			)
		else
			(= success FALSE)
		)
		(return success)
	)

	(method (eatMeal &tmp haveEaten)
		(= haveEaten FALSE)
		(cond
			(freeMeals
				(-- freeMeals)
				(= haveEaten TRUE)
			)
			(((inventory at: iFood) amount?)
				((inventory at: iFood)
					amount: (- ((inventory at: iFood) amount?) 1)
				)
				(if (not ((inventory at: iFood) amount?))
					((inventory at: iFood) owner: 0 realOwner: 0)
				)
				(= haveEaten TRUE)
			)
			((Btst fStarving)
				(if (self useStamina: 8 0)
					(messager say: N_EGO NULL C_STARVING 0 0 modNum)
				else
					(EgoDead deathSTARVATION)
				)
			)
			((Btst fHungry)
				(Bset fStarving)
				(messager say: N_EGO NULL C_HUNGRY 0 0 modNum)
			)
			(else
				(Bset fHungry)
				(messager say: N_EGO NULL C_NO_MORE_RATIONS 0 0 modNum)
			)
		)
		(if haveEaten
			(cond 
				((Btst fStarving)
					(Bclr fStarving)
				)
				((Btst fHungry)
					(Bclr fHungry)
				)
			)
		)
	)

	(method (takeDamage damage)
		(SkillUsed VIT (Abs damage))
		(if (< (-= [egoStats HEALTH] damage) 0)
			(= [egoStats HEALTH] 0)
		)
		(if (> [egoStats HEALTH] (MaxHealth))
			(= [egoStats HEALTH] (MaxHealth))
		)
		;did ego survive?
		(return (> [egoStats HEALTH] 0))
	)

	(method (useStamina pointsUsed &tmp foo noStaminaMsg)
		(if (< argc 2)
			(= noStaminaMsg TRUE)
		else
			(= noStaminaMsg FALSE)
		)
		(if (> pointsUsed 0)
			(SkillUsed VIT (>> (+ pointsUsed 4) 2))
		)
		(cond 
			((> (= foo (-= [egoStats STAMINA] pointsUsed)) 4)
				(Bclr fWornOut)
				(if (> foo (MaxStamina))
					(= [egoStats STAMINA] (MaxStamina))
				)
			)
			((>= foo 0))
			((TakeDamage (>> (- 2 [egoStats STAMINA]) 2))
				(= [egoStats STAMINA] 0)
				(if (not (Btst fWornOut))
					(Bset fWornOut)
					(if noStaminaMsg
						(messager say: N_EGO NULL C_WORN_OUT 0 0 modNum)
					)
				)
			)
			((> [egoStats HEALTH] 0))
			(noStaminaMsg
				(EgoDead deathOVERWORK)
			)
		)
		(return [egoStats HEALTH])
	)
	
	(method (useMana pointsUsed)
		(if [egoStats MAGIC]
			(if (< (-= [egoStats MANA] pointsUsed) 0)
				(= [egoStats MANA] 0)
			)
			(if (> [egoStats MANA] (MaxMana))
				(= [egoStats MANA] (MaxMana))
			)
		)
		(return [egoStats MANA])
	)

	(method (maxHealth)
		(return (/ (+ [egoStats STR] [egoStats VIT] [egoStats VIT]) 3))
	)
	
	(method (maxStamina)
		(return (/ (+ [egoStats AGIL] [egoStats VIT]) 2))
	)
	
	(method (maxMana &tmp egoMagic)
		(return
			(if (= egoMagic [egoStats MAGIC])
				(return (/ (+ [egoStats INT] egoMagic egoMagic) 3))
			else
				0
			)
		)
	)
	
	(method (castSpell spellNum)
		(return
			(if (< [egoStats MANA] [spellCost (- spellNum OPEN)])
				(messager say: N_EGO NULL C_NO_MANA 1 0 modNum)
				(return FALSE)
			else
				(return
					(self
						useMana: [spellCost (- spellNum OPEN)]
						trySkill: spellNum 0
					)
				)
			)
		)
	)
	
	(method (get what howMany &tmp obj num oldNum)
		(= num (if (== argc 1) 1 else howMany))
		(= oldNum ((inventory at: what) amount?))
		(if (< (+= num oldNum) 0)
			(= num 0)
		)
		(if (and (!= what iMoney) (== num 0))
			((inventory at: what)
				amount: num
			)
			(theIconBar
				curInvIcon: 0
				disable: ICON_USEIT
				curIcon: (theIconBar at: ICON_WALK)
			)
		else
			((inventory at: what) amount: num owner: self)
		)
		(return (- num oldNum))
	)
	
	(method (has what)
		(return
			(if (== what iMoney)	;always have the money pouch as TRUE
				(return TRUE)
			else
				(return ((inventory at: what) amount?))
			)
		)
	)
	
	(method (use what howMany &tmp obj num oldNum)
		(= obj (inventory at: what))
		(if
			(>
				(= num (if (== argc 1) 1 else howMany))
				(obj amount?)
			)
			(= num (obj amount?))
		)
		(self get: what (- num))
		(if (< (obj amount?) 1)
			;have 0 or a negative number of the item left,
			; so actually remove it from the inventory
			(obj
				amount: 0
				realOwner: 0
				owner: 0
			)
			(theIconBar
				curInvIcon: 0
				advanceCurIcon:
			)
		)
		(return num)
	)
	
	(method (learn what howWell &tmp obj num)
		(= num (if (== argc 1) 5 else howWell))
		(if (and [egoStats MAGIC] (> num [egoStats what]))
			(= [egoStats what] num)
		)
		(return [egoStats what])
	)

	(method (addHonor howMuch &tmp i)
		(/= howMuch 5)
		(if (< howMuch 0)	;lose honor
			(for ((= i howMuch)) (< i 0) ((++ i))
				(self useSkill: HONOR -15)
			)
		else	;gain honor
			(for ((= i 0)) (< i howMuch) ((++ i))
				(self useSkill: HONOR 15)
			)
		)
	)
)

(instance egoStopWalk of StopWalk)