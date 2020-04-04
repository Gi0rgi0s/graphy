HEIGHT=30; WIDTH = 60

def radius = 14
def xOffset = 0//10 
def yOffset = 0 

for(int i = 0; i < HEIGHT; i++) {
  for(int j = 0; j < WIDTH; j++) {
    A2 = (WIDTH/2-j-xOffset) * (WIDTH/2-j-xOffset)
    B2 = (HEIGHT/2-i-yOffset) * (HEIGHT/2-i-yOffset)
    if(A2+B2 < radius*radius) printSlice(HEIGHT/2-i,WIDTH/2-j) else print '  '
  }
  println('.')
}

def printSlice(def i, def j) {
  rad = Math.atan((i+0.01)/(j+0.01))
  print(Math.round(rad))
}
