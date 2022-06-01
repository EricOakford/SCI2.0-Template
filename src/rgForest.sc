;;; Sierra Script 1.0 - (do not remove this comment)
(script# rgFOREST)
(include game.sh) (include "801.shm")
(use Main)
(use Feature)
(use PolyPath)
(use HandsOffScript)
(use Polygon)
(use Sound)
(use Print)
(use Motion)
(use Game)
(use Actor)
(use System)

(public
	Forest 0
	sleepInForest 1
)

(local
	atClearing
	initEgoX
	initEgoY
	walkInDone
	toX
	toY
	floorPoly
)

(instance Forest of Region
	(properties
		modNum rgFOREST
		noun N_FOREST
	)
	
	(method (init &tmp pic sound)
		(super init:)
		(if
			(and
				(<= 704 (= pic (curRoom picture?)))
				(<= pic 707)
			)
			(= atClearing TRUE)
		)
		(= sound (if Night sForestNight else sForestDay))		
		(if (!= (theMusic number?) sGuildQFG1)
			(theMusic
				number: sGuildQFG1
				setLoop: -1
				setPri: 0
				play:
			)
		)
		
		(= initEgoX 0)
		(= initEgoY 0)
		(switch (ego edgeHit?)
			(NORTH
				(= initEgoX 160)
				(= initEgoY 230)
			)
			(SOUTH
				(= initEgoX 145)
				(= initEgoY 56)
			)
			(EAST
				(= initEgoX -30)
				(= initEgoY 110)
			)
			(WEST
				(= initEgoX 350)
				(= initEgoY 110)
			)
		)

		;rooms 166, 167, and 168 have their own pics and polygons set,
		; so we don't need to set those here
		(switch curRoomNum
			(112
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 103
							204 103
							162 80
							165 26
							145 25
							126 82
							0 80
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 118
							123 169
							210 168
							319 135
							319 189
							0 189
						yourself:
					)
				)
			)
			(118
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 65
							221 70
							170 63
							144 67
							141 102
							106 118
							0 102
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 144
							126 176
							204 176
							210 132
							254 119
							319 119
							319 189
							0 189
						yourself:
					)
				)
			)
			(125
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 189
							203 189
							208 128
							234 83
							166 66
							153 39
							128 39
							149 89
							121 117
							0 109
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 145
							89 158
							140 189
							0 189
						yourself:
					)
				)
			)
			(126
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 103
							198 103
							162 80
							156 26
							131 26
							131 80
							115 80
							58 110
							118 147
							124 189
							0 189
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							216 189
							248 152
							319 136
							319 189
						yourself:
					)
				)
			)
			(127
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 65
							221 70
							170 63
							144 67
							141 102
							106 118
							0 102
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 144
							126 176
							204 176
							210 132
							254 119
							319 119
							319 189
							0 189
						yourself:
					)
				)
			)
			(133
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 94
							160 61
							160 37
							126 37
							128 61
							143 79
							139 107
							85 147
							133 189
							0 189
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							218 189
							206 143
							228 124
							319 136
							319 189
						yourself:
					)
				)
			)
			(136
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							139 27
							128 99
							0 103
							0 0
							319 0
							319 189
							197 189
							200 140
							221 114
							163 71
							161 27
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 138
							98 146
							119 167
							121 189
							0 189
						yourself:
					)
				)
			)
			(142
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 94
							160 61
							160 37
							126 37
							128 61
							143 79
							139 107
							85 147
							133 189
							0 189
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							218 189
							206 143
							228 124
							319 136
							319 189
						yourself:
					)
				)
			)
			(152
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 67
							169 67
							154 36
							132 36
							145 95
							104 120
							0 101
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 148
							132 172
							238 172
							199 159
							197 147
							319 110
							319 189
							0 189
						yourself:
					)
				)
			)
			(156
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							135 82
							104 92
							0 88
							0 0
							319 0
							319 99
							198 99
							154 65
							154 26
							135 26
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							216 122
							319 122
							319 189
							201 189
							201 139
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 102
							106 122
							121 152
							121 189
							0 189
						yourself:
					)
				)
			)
			(162
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 71
							178 72
							146 34
							127 34
							145 89
							123 114
							45 114
							0 91
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 141
							140 173
							140 189
							0 189
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							208 137
							319 111
							319 189
							208 189
						yourself:
					)
				)
			)
			(163
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							135 82
							104 92
							0 88
							0 0
							319 0
							319 99
							198 99
							154 65
							154 26
							135 26
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							216 122
							319 122
							319 189
							201 189
							201 139
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 102
							106 122
							121 152
							121 189
							0 189
						yourself:
					)
				)
			)
			(177
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							149 104
							94 116
							0 100
							0 0
							319 0
							319 67
							183 67
							165 59
							157 29
							138 29
							138 59
							149 76
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 140
							92 140
							122 171
							201 171
							201 139
							221 123
							319 124
							319 189
							0 189
						yourself:
					)
				)
			)
			(179
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 67
							169 67
							154 36
							132 36
							145 95
							104 120
							0 101
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 148
							132 172
							238 172
							199 159
							197 147
							319 110
							319 189
							0 189
						yourself:
					)
				)
			)
			(180
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							128 78
							109 89
							0 89
							0 0
							319 0
							319 104
							203 104
							167 65
							157 29
							138 29
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 109
							99 133
							122 171
							201 171
							201 139
							221 123
							319 124
							319 189
							0 189
						yourself:
					)
				)
			)
			(185
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							93 101
							0 85
							0 0
							319 0
							319 99
							190 101
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 122
							106 122
							121 166
							203 166
							202 132
							319 132
							319 189
							0 189
						yourself:
					)
				)
			)
		)
		(cond
			((OneOf curRoomNum 118 125 127 133 142 152 162 179)
				(= floorPoly
					((Polygon new:)
						type: PTotalAccess
						init:
							143 166
							98 139
							98 117
							129 117
							148 92
							149 67
							175 67
							185 73
							262 76
							264 109
							200 137
							200 184
							143 184
						yourself:
					)
				)
			)
			((OneOf curRoomNum 117 135 151 157 171 181)
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 96
							178 68
							152 42
							125 42
							148 89
							123 116
							0 83
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 140
							114 168
							133 189
							0 189
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							221 189
							319 145
							319 189
						yourself:
					)
				)
				(= floorPoly
					((Polygon new:)
						type: PTotalAccess
						init:
							147 88
							226 88
							226 139
							212 139
							212 182
							137 182
							130 162
							99 147
							147 87
						yourself:
					)
				)
			)
			((OneOf curRoomNum 111 123 144)
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							177 95
							131 113
							127 128
							35 154
							19 189
							0 189
							0 0
							319 0
							319 75
							248 81
						yourself:
					)
				)
				(= floorPoly
					((Polygon new:)
						type: PTotalAccess
						init:
							125 185
							125 128
							190 95
							220 95
							235 85
							315 85
							315 102
							281 102
							218 129
							206 146
							195 169
							193 185
						yourself:
					)
				)
			)
			((OneOf curRoomNum 119 143 186)
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							319 0
							319 189
							196 189
							223 147
							176 109
							82 82
							0 75
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 118
							128 189
							0 189
						yourself:
					)
				)
				(= floorPoly
					((Polygon new:)
						type: PTotalAccess
						init:
							126 181
							39 100
							45 84
							86 85
							174 107
							192 125
							192 183
							126 182
						yourself:
					)
				)
			)
			((== curRoomNum 169)
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							108 6
							103 60
							86 82
							85 97
							70 117
							129 132
							207 147
							319 147
							319 189
							0 189
							0 95
							0 0
							319 0
							319 67
							271 67
							178 51
							176 23
							164 6
						yourself:
					)
				)
				(= floorPoly
					((Polygon new:)
						type: PTotalAccess
						init:
							115 155
							102 125
							102 110
							143 87
							141 73
							158 72
							193 93
							218 93
							218 116
							173 155
						yourself:
					)
				)
			)
			((OneOf curRoomNum 175 192)
				(curRoom addObstacle:
					((Polygon new:)
						type: PBarredAccess
						init:
							0 0
							165 0
							140 52
							0 79
						yourself:
					)
					((Polygon new:)
						type: PBarredAccess
						init:
							0 114
							83 114
							131 137
							244 116
							211 52
							211 0
							319 0
							319 189
							0 189
						yourself:
					)
				)
				(= floorPoly
					((Polygon new:)
						type: PTotalAccess
						init:
							69 111
							68 68
							142 54
							161 15
							206 15
							206 54
							226 88
							227 110
							201 121
							128 131
							84 110
						yourself:
					)
				)
				(if (== (ego edgeHit?) SOUTH)
					(= initEgoX 170)
				)
			)
		)
		;(curRoom canSleep: TRUE)	;can sleep in any forest room unless stated otherwise
		;only init the floor feature if it's been given polygons in that room
		(if floorPoly
			(forestFloor
				init:
				setPolygon: floorPoly
			)
		)
		(if
			(and
				initEgoX
				initEgoY
			)
			(ego posn: initEgoX initEgoY)
		else
			(ego posn: 160 140)			
		)
		(ego
			init:
			normalize:
		)
	)
	
	(verbs
		(V_SLEEP
			(ego setScript: sleepInForest)
		)
	)
)


(instance forestFloor of Feature
	(properties
		noun N_FLOOR
		modNum rgFOREST
	)
)

(class Bush of View
	(properties
		view 700
		noun N_BUSH
		modNum rgFOREST
	)
)

(class Snow of View
	(properties
		view 702
		noun N_SNOW
		modNum rgFOREST
	)
)

(instance sleepInForest of HandsOffScript
	(method (changeState newState)
		(switch (= state newState)
			(0
				(ego setHeading: 180 self)
			)
			(1
				(ego
					view: 551
					setLoop: 0
					setCel: 0
					posn: (- (ego x?) 3) (+ (ego y?) 2)
					setCycle: EndLoop self
				)
			)
			(2
				(= ticks 12)
			)
			(3
				(ego
					setLoop: 1
					setCel: 0
					posn: (- (ego x?) 4) (+ (ego y?) 1)
					setCycle: EndLoop self
				)
			)
			(4
				(PalVary PalVaryStart (curRoom picture?) 2)
				(if (curRoom nightPalette?)
					(PalVary PalVaryTarget (curRoom nightPalette?))
				)
				(= seconds 5)
			)
			(5
				(= seconds 2)
			)
			(6
				(PalVary PalVaryReverse 4)
				(= seconds 2)
			)
			(7
				(= seconds 2)
			)
			(8
				((ScriptID TIME 0) init: 5 0)
				(ego setCycle: BegLoop self)
			)
			(9
				(ego
					setLoop: 0
					setCel: 6
					posn: (+ (ego x?) 4) (- (ego y?) 1)
					setCycle: BegLoop self
				)
			)
			(10
				(ego
					posn: (+ (ego x?) 3) (- (ego y?) 2)
					normalize:
				)
				(self dispose:)
			)
		)
	)
)
