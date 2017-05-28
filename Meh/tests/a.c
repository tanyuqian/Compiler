class Edge{
    int to;
    int next;
}

Edge[] e;
int ne = 0;
int n = 0;

void init(){
    e = new Edge[100];
    ne = 0;
}

int main(){

    n = 7;
    e = new Edge[100];
    ne = 0;
    e[0].to = 2;

    return 0;
}