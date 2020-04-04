HEIGHT=40; WIDTH = 80

def radius = 15
def xOffset = 0; def yOffset = 0
def xOrigin = WIDTH/2; def yOrigin = HEIGHT/2

for(int i = 0; i < HEIGHT; i++) {
  for(int j = 0; j < WIDTH*2; j++) {

    def adj = j-(xOffset-xOrigin)
    def opp = i-(yOffset-yOrigin)
    
    def mag = distance(adj,opp)

    if(mag <= radius) {
        printSlice(radius,adj,opp) 
    } else if(mag < radius + 1 ) antiAlias(radius,adj,opp)

    else print '.'
  }
  println('.')
}

def antiAlias(radius,adj,opp) {
    def map = [:]

    map['e'] = distance(adj+1,opp) <= radius ? 1 : 0
    map['ne'] = distance(adj+1,opp+1) <= radius ? 1 : 0
    map['n'] = distance(adj,opp+1) <= radius ? 1 : 0
    map['nw'] = distance(adj-1,opp+1) <= radius ? 1 : 0
    map['w'] = distance(adj-1,opp) <= radius ? 1 : 0
    map['sw'] = distance(adj-1,opp-1) <= radius ? 1 : 0
    map['s'] = distance(adj,opp-1) <= radius ? 1 : 0
    map['se'] = distance(adj+1,opp-1) <= radius ? 1 : 0

    vote = map.values().sum()

}

def printSlice(def radius, def i, def j) {
//   if((radius+1)*(radius+1) - i*i + j*j < 100 ) print ('*·') else {
   print '•' //} //·
}

def distance(adj,opp) {
    return Math.sqrt(adj*adj+opp*opp)
}
