#include <iostream>

using namespace std;

int main() {
    //A square and Rectangle
    const int rows=5;
    const int cols=10;
    const string starRow(cols,'*');
    
    for(int i=0; i!=rows; ++i)
    {
        cout<<starRow<<endl;
    }

    //Triangle
    for(int i=0; i!=rows; ++i)
    {
        string star(i,'*');
        cout<<star<<endl;
    }
    

  return 0;
}
