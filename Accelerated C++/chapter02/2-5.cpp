#include <iostream>
#include <string>

using namespace std;

int main()
{
    /*
    for square we have rows = cols but for
    rectangls rows and cols are not equal
   */

   cout << "Which shape you want to print? 1. square 2. rectangle 3. Triangle: " << endl;
   int choice;
   cin >> choice;
   int rows;
   int cols; 
   string::size_type c = 0;
 
   if (choice == 1 || choice == 2)
   {
        cout << "Enter Length: ";
        cin >> cols;
        rows = cols;
        cout << endl;
       
       const string spaces(cols - 2, ' ');
       for (int r = 0; r < rows; r++)
       {
           for ( c = 0; c < cols; c++)
           {
               
               if (r == 0 || r == rows -1 || c == 0 || c == cols -1)
               {
                   
                //    cout << "r = " << r << " c = " << c << endl;
                   cout << "* ";
               }
               else
               {
                   cout << spaces << spaces;
                   c += (spaces.size()-1);// or you can print spaces one character at a time by replacing both line with only cout << "  ";
               }
               
               
               
           }
           
           cout << endl;
       }
       

   }
   else
   {
       /*                             *
                         *           * *
                *       * *         * * *
         *     * *     * * *       * * * *
    *   * *   * * *   * * * *     * * * * *
    */    
        cout << "Enter base: ";
        cin >> rows;
        
        int r = 0;
        int c = rows;
       for (int r = 0; r < rows; r++)
       {
           
           for ( c = rows; c > r; c--)
           {
               /* code */
               cout << " ";
             
               
           }
            for (int star = 0; star <= c; star++)
            {
                /* code */
                cout << "* ";

            }
           cout << endl;
           
       }
       
       
       
   }
   

   

    return 0;
}
