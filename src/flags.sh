;;; Sierra Script 1.0 - (do not remove this comment)
;Flag handler defines
;NOTE: These are intended to replace the Bset, Btst, and Bclr procedures.
;However, SCICompanion does not yet support macro defines.
;;;(define Bset	gameFlags set:)
;;;(define Btst	gameFlags test:)
;;;(define Bclr	gameFlags clear:)
(define NUMFLAGS 128)

;Event flags
(enum
	fTimeStopped
	fOverloaded
	fHungry
	fStarving
	fWornOut
	fLeprechaunGold
	fMetMerv
	fBoughtMeepToy
	fBoughtSleepingPills
	fRobbedMerv
	fBeenInGraveyard
	fEnteredSpielburg
	fMetHippies
	fHippiesDead
	fWearingAmulet
	fBeenInDungeon
	fAskedAboutGhost
	fSawGhost
	fSearchedRogue
	fBeenInGuild
	fSignedLogbook
	fInvitedByBros
	fMetLawyer
	fPaidSettlement
	fRobbedLawyer
	fMetMonk
	fLarryGotBonehead
	fBeenInTreehouse
	fMetWitch
	fBeenInStarbucks
	fKasparBackInShop
	fLootedCashRegister
	fMetHealer
	fRobbedHealer
	fHealerRapesEgo
	fBeenInCavemanArea
	fMetCaveman
	fSavedPterry
	fGargoyleHired
	fGotInflatableBear
	fBeenAtErasmusEntrance
	fMonsterDazzled
	fMonsterRecoils
	fSaveAllowed
	fBattleStarted
	fRichardDead
	fSavedElsa
	fDiedInBattle
	fNextMonster
	fBeenInMafiaDen
	fKnowCombination
	fMobstersArrested
	fMetWolfgang
	fMetBigJurg
	fMetLilJurg
	fAskedAboutCalling
	fBigJurgNaked
	fGaveClothesToBrauggi
	fAgreedToHelpHilde
	fTendedFarmToday
	fFirstTendedFarm
	fAgreedToHelpBill
	fDidLaundryToday
	fFirstDidLaundry
	fHildeAgreedToDate
	fDatedHilde
	fPaidForFarmWorkToday
	fPaidForLaundryWorkToday
	fMetHilde
	fScrewedHilde
	fMetBagi
	fMetMobster
	fMetBartender
	fGotDragonsBreath
	fGotBeerKeg
	fMetKasparInAlley
	fMetToiletGuy
	fRobbedToiletGuy
	fBeenInSheriffsOffice
	fMetJailedButcher
	fSawButcherInJail
	fSawMobstersInJail
)

;Puzzle point flags
(enum
	f113RobLawyer		;-5 points
	f113PaySettlement	;10 points
	f115TalkToCaveman	;2 points
	f155RobHealer		;-5 points
	f165TalkToMerv		;2 points
	f165RobMerv			;-5 points
	f165BuySomething	;2 points
	f207TalkToMonk		;2 points
	f207GetShovel		;2 points
	f207DigUpBones		;2 points
	f207DigUpMoney		;2 points
	f300TalkToWitch		;2 points
	f306KillBeaudine	;20 points
	f400EnterTown		;1 point
	f401GetEvidence		;20 points
	f411SignLogBook		;5 points
	f430TalkToBartender	;2 points
	f433TalkToKaspar	;2 points
	f645BeatGoblin		;2 points
	f650BeatGuard		;2 points
	f655BeatRogue		;10	points
)
