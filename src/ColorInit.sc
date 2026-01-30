;;; Sierra Script 1.0 - (do not remove this comment)
;
;	COLORINIT.SC
;
;	This script initializes the global variables for colors.
;	It gathers all the color assignments based on their proportions of
;	red, green, and blue, and thus will display the correct color regardless
;	of the palette or graphics driver.
;
;	In SCI32, EGA support was removed entirely, making this list more for
;	programmer convenience than anything else.
;
		
;
(script# COLOR_INIT)
(include game.sh)
(use Main)
(use System)

(public
	colorInit 0
)

(instance colorInit of Code
	(method (doit)
		;initialize the colors
		(= colBlack 	(Palette PalMatch 31 31 31))
		(= colGray1 	(Palette PalMatch 63 63 63))
		(= colGray2 	(Palette PalMatch 95 95 95))
		(= colGray3 	(Palette PalMatch 127 127 127))
		(= colGray4 	(Palette PalMatch 159 159 159))
		(= colGray5 	(Palette PalMatch 191 191 191))
		(= colWhite		(Palette PalMatch 223 223 223))
		(= colDRed		(Palette PalMatch 151  27  27))
		(= colLRed		(Palette PalMatch 231 103 103))
		(= colVLRed		(Palette PalMatch 235 135 135))
		(= colDYellow	(Palette PalMatch 187 187 35))
		(= colYellow	(Palette PalMatch 219 219 39))
		(= colLYellow	(Palette PalMatch 223 223 71))
		(= colDGreen	(Palette PalMatch 27 151 27))
		(= colLGreen	(Palette PalMatch 71 223 71))
		(= colVLGreen	(Palette PalMatch 135 235 135))
		(= colDBlue		(Palette PalMatch 23 23 119))
		(= colBlue		(Palette PalMatch 35 35 187))
		(= colLBlue		(Palette PalMatch 71 71 223))
		(= colVLBlue	(Palette PalMatch 135 135 235))
		(= colMagenta	(Palette PalMatch 219 39 219))
		(= colLMagenta	(Palette PalMatch 223 71 223))
		(= colCyan		(Palette PalMatch 71 223 223))
		(= colLCyan		(Palette PalMatch 135 235 235))
		
		(DisposeScript COLOR_INIT)	;don't need this in memory anymore

	)
)