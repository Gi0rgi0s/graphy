HEIGHT=30.0; WIDTH = 60.0

def radius = 15
def xOffset = 0
def yOffset = 0 

list = args[0].split(',').toList()*.toInteger()
sum = list.sum()
list = list.collect{it/sum*2*Math.PI}
println "LIST LIST LIST $list"

for(int i = HEIGHT; i > 0; i--) {
  for(int j = 0 ;j < WIDTH*2; j++) {
    adj = ((j/2)-xOffset-WIDTH/2) 
    opp = (i-yOffset-HEIGHT/2)
    A2 = adj*adj
    B2 = opp*opp
    if(A2+B2 < radius*radius && A2+B2>= 20) printSlice(adj,opp,list) else print ' '
  }
  println(' ')
}

def printSlice(def i, def j, def list) {
  // println("$i $j")
  def pi = Math.PI
  def quad = 0
  def angRef = 1
 
  if( i<=0 && j >= 0 ) {
    quad = pi/2
    angRef=-1
  } else if( i<=0 && j <=0 ) {
    quad = pi
    angRef=1
  } else if( i>=0 && j<=0 ) {
    angRef = -1
    quad = 3/2*pi 
  }

  double rad = Math.atan(Math.pow(Math.abs(j+0.001)/Math.abs(i+0.001),angRef))
  
  index = getIndex(list, rad+quad)
  // print index
  if(index % 2 == 1) print('😃') else { print '🦄' }

  // plotMe = (rad)+quad < 3/2*pi + pi/3
  // print "|"
  // print rad.round(1) 
  // if(plotMe) print(',') else { print '•' }

//◦•
//  print '••' 
} 

def getIndex(def list, def angle) {
  // println ""
  // println "$angle ANGEL ANGLE NAGLE NAGKE"
  // println ""

  for(int i=0; i < list.size(); i++ ) {
    angle = angle - list[i]
    // println "$i $list"
    if(angle <= 0 ) {     
      return i;
    }
  }
  return list.size()

}