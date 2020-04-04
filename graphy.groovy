//TODO X-axis 
//auto-adjusting width
//parameters
//legend
//refactor
//PIE chart
//fractions, doubles
//negative numbers

// xBar X coordinate relative to current bar
// yBar Y coordinate relative to current bar

//TODO HEIGHT > 0
int WIDTH = 60; int HEIGHT = 30; int NUM_MAJOR = Math.sqrt(HEIGHT).toInteger()

// list = [9,3,101,100,15,22,1,40,20,17,5,8,19,9,3,11,15,22,1,40,20,17]

// list = [1,2,3,4,5,6,11,2]

list = args[0].split(',').toList()*.toInteger()
count = list.size()

int barWidth = WIDTH/count
int key = (WIDTH/(barWidth)).intValue()

createGraph(list,HEIGHT,WIDTH,NUM_MAJOR)

def createGraph(def list, def HEIGHT, def WIDTH, def NUM_MAJOR) {

  int count = list.size(); int maxVal = list.max().toInteger();
  if(NUM_MAJOR >= maxVal) NUM_MAJOR = (int)(maxVal + 1)
  int barWidth = WIDTH/count
  def smoothing = HEIGHT%NUM_MAJOR
  HEIGHT = HEIGHT - smoothing
  int freqMajor = (HEIGHT)/NUM_MAJOR

  def mag = 10**(Math.floor( Math.log10(maxVal)) )
  
  if(WIDTH<list.size()) WIDTH = list.size()

  def remainder = (mag/10*NUM_MAJOR).toInteger()
  if(remainder <= 0) {remainder = NUM_MAJOR}
  def yAxisMax = maxVal + remainder - (maxVal % remainder)
  
  double scaleFactor = (double) (HEIGHT / yAxisMax)

  def padding = WIDTH % count
  
  def rightPad = (int) (WIDTH - (padding - padding / 2))
  def leftPad = (int) (padding / 2)
  def barMiddle = (int) (barWidth/2)
  def hasFrame = barWidth > 2

  println("num_major: $NUM_MAJOR bar_width: $barWidth has_frame: $hasFrame")
  println("smoothing: $smoothing y_axis_max $yAxisMax y_inc: ${yAxisMax/NUM_MAJOR} size: ${list.size()}")
  println("scaleFactor: $scaleFactor padding: $padding bar_value: ${1/scaleFactor}")

  // START GRAPHING BELOW!!!!!!!

  for(int row = -smoothing; row < HEIGHT+3; row++ ) {
    // print(row)

    for(int col = 0; col < WIDTH; col++ ) {
        printRow( row, col, leftPad, rightPad, HEIGHT, WIDTH, barWidth, list, scaleFactor, freqMajor)   
    }
    printEndOfRow(row, freqMajor, rightPad, HEIGHT, scaleFactor)
  }
}

def printRow(def row, def col, def leftPad, def rightPad, def HEIGHT, def WIDTH, def barWidth, def list, def scaleFactor, def freqMajor) {

  //Graphable Area Coordinates
  int graphX = col - leftPad
  int graphY = HEIGHT
  
  int key = (graphX/(barWidth)).intValue()
  int barValue = asciiToInt((list[key] ? list[key] : -1) )
  int barX = ((col-leftPad) + barWidth) % (barWidth)
  int numLength = getNumberLength(barValue)

  double barHeight = (barValue * scaleFactor).round(6)
  int barY = row + (barHeight-HEIGHT)
  
  //if the cursor above the bottom bound
  if(row < graphY) {
    //if cursor is in bar and cursor is in bounds
    if( (row >= barY && barY >=0 ) && (col <= (rightPad) && col >= leftPad) ) {
      //if cursor is in bar and cursor is in bounds
      if( barWidth > 2 && barX == barWidth - 1) {
        xPrintFrame(barWidth)
      //if cursor is at top of bar
      } else if(graphY >= barHeight + row) {
        printTopBar(barX, row, HEIGHT, barHeight, barWidth, barValue)
      //if the cursor is in the bar
      } else {
        xPrintBar()
      }
    } else {
      // print '-'
      printLine(col, row, rightPad, freqMajor)
    }
  //if the cursor is at the xAxis
  } else if(row == HEIGHT) { 
    print "█"
    //if the cursor below the xAxis
  } else if(barValue >= 0) { 
    drawXAxis(barWidth, barX, barValue) 
  }
}

def printEndOfRow(def row, def freqMajor, def rightPad, def HEIGHT, def scaleFactor) {
  //if this is at the major line interval
  if(row % freqMajor == 0) {
    println((int)Math.round( (double)(((HEIGHT-row)/scaleFactor))) )
  } else {
    println('')
  }
}

def xPrintBar() {
  print('░')
  // print '╳'
  // print('║')
  // print('█')
  // print('▒')
  // print('▒')
}

def xPrintFrame(def barWidth) {
  //if bar width > 2, print a frame
  if(barWidth > 2) print('·') else xPrintBar() 
}

def asciiFraction(double value) {
    // ▁ ▂ ▄ ▅ ▆ ▇ █ ▉ ▊
    value = value.round(6)
    if(value >= 99999/100000) return '░'
    if(value >= 8/9) return '⣿'
    if(value >= 7/9) return '⣾'
    if(value >= 6/9) return '⠿'
    if(value >= 5/9) return '⠾'
    if(value >= 4/9) return '⠶'
    if(value >= 3/9) return '⠴'
    if(value >= 2/9) return '⠴'
    if(value >= 1/9) return '⠤'
    return '.'
}

def printTopBar(int xBar, int y, def height, double barHeight, int barWidth, int barValue) {

  def hasFrame = barWidth > 2

  int numLength = getNumberLength(barValue)
  if(hasFrame) barWidth = barWidth - 1
  
  //TODO clean these fake booleans up
  int barMiddle = barWidth/2
  //0 - 0
  
  
  int fudge = 0//1 - barWidth % 2 + numLength % 2 
  if(barWidth % 2 == 0 &&  numLength % 2 == 0) fudge = 1

  if(barWidth == 1 && xBar == 0) fudge = 0
  int numMiddle = (numLength)/2 - ( ((numLength+1) % 2) * ((numLength+1) % 2) )
  
  //TODO clean these calculations up
  double scaleFactor = barHeight/barValue
  
  //ASCIIASCIIASCIIASCIIASCIIASCII
  double currentBar = ((height-y)/scaleFactor).round(1)
  double previousBar = (currentBar - barValue/barHeight)
  double increment = barValue/barHeight
  double diff = (barValue - previousBar) / increment
  //ASCIIASCIIASCIIASCIIASCIIASCII
  
  if( (barMiddle == numMiddle + xBar + fudge) && (barWidth >= numLength) ) {
  //       1      ==    1       + 0    + 1 <>        3     >=    3
    // print "$barMiddle == $numMiddle + $xBar + $fudge - $numLength"
    print(barValue)
    // print "X"
  } else if( (numLength > barWidth) && (xBar  <= (barWidth) ) ||
             (numLength <= barWidth) && (xBar + numLength  <= (barWidth) )
   ) { //0 + 2 = 1  //0 == 0 + 0 + 0 - 2 false 1
    // print('*')
    print asciiFraction(diff)
    //  print "$barMiddle == $numMiddle + $xBar + $fudge <> $barWidth >= $numLength $hasFrame" 
    // print barWidth
    // print("U")
    // print "A"
  }// else print "$barMiddle == $numMiddle + $xBar + $fudge <> $barWidth >= $numLength $hasFrame" 
}      //0 == 0 + 0 + 0 - 2 false 1
       //0 == 0 + 0 + 0 - 2 false 1

  // if( (!hasFrame && (barWidth > xBar + numLength)) || hasFrame && ( (barWidth+2) > xBar+ numLength || (xBar + numMiddle)  barMiddle ) ) {
//   if( (!hasFrame && (barWidth > xBar + numLength)) || hasFrame && ( (barWidth+2) > xBar+ numLength || (xBar + numMiddle)  barMiddle ) ) {
//     //  print("T") //barMiddle==1
//     print asciiFraction(diff)
//   } else if( xBar + numMiddle == barMiddle) {
//     print(barValue)
//     //  print(!hasFrame)  
//     // print("width1 $barWidth length $numLength")
//     // print(numLength*10**(numLength-1))
//   } else if( xBar > leadUnits + barMiddle ) {
//       //  print("width2 $barWidth length $numLength")
//     // print("B")
//     print(asciiFraction(diff))
//   }
// }

def isOdd(int value) {
  return (value%2 == 1)
}

def printLine(int x, int y, int rightPad=-1, int minor_freq=-1) {
  if (y % minor_freq == 0 && y != rightPad) { 
    print ('~') 
  } else if (y % minor_freq == 0 && y == rightPad) { 
    print "X" 
  } else { 
    print('┄')//print('·')
  }
}

def middleIntersection(int staticNum, int dynamicNum, def roundRight = true) {
    
}

def drawXAxis(int barWidth, int xBar, double barValue) {
  int numLength = 3//getNumberLength(barValue)
  int barMiddle = barWidth/2
  int numMiddle = (numLength/2) 
                        
  boolean isBarMiddle = ((barWidth+1)/2) == xBar-1 // xBar+1 is used to avoid div/0 error
  // if( xBar !=0 && ( isBarMiddle && barValue !=0)) 
  if( xBar == barMiddle )
  print "║" else print ' '
}

def getNumberLength(def length) {
  return (int) Math.log10(length) + 1
}

def asciiToInt(def asciiString) {
  return asciiString.toInteger()
}

// 71 

//993 -> 1200, mag: 100. 993 +  (60 - (993 % 60)) -> 1020

//maxVal + (mag/10*NUM_MAJOR- (maxVal % 60)) -> 1020


//933 -> 260 + (60 - (260 % 60)) -> 300
//260 -> 300, mag: 100, 260 + (60 - (260 % 60)) -> 300
//445 -> 600, mag: 100, (60 - (445 % 60)) -> 600
//500 -> 600, mag: 100, 445 + (60 - (445 % 60)) -> 60
//850 -> 900, mag: 100
//559 -> 600, mag: 100
//11 -> 12, mag: 10, 11 + 6 - (11 % 6) ->
//99 -> 120, mag 10

// num minor 6
// height 71
//   67  
//    7  8
//   17   
//   23
//   39
//   71
//  149
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//



// yAxisMax, heighest multiple of num_major < HEIGHT

// 70, 35, 3

// 70, 140, 

// 71, 140, 
// 69, 140, 


// NUM_MINOR=6




// yAxisMax = barValue*NUM_MAJOR


// 1. less than height
// 2. bigger than maxVal




// int correction = (numLength + 1 ) % 2
  // int cc = (numLength%2) * ((barWidth+1) % 2)
  // int leadUnits =  numMiddle + ((numLength+1) % 2) * ((barWidth) % 2) - ((numLength+1) % 2) * ((barWidth+1) % 2)
  // if( !(barWidth-numLength == 0) && barWidth-1 != (numLength) && ( (barWidth - 2) < numLength || (xBar + numMiddle) < barMiddle ) ){  
