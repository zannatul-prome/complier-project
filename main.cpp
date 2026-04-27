#include <fstream>
#include <sstream>

int main()
{
    ifstream file("program.txt");
    string line;

    while (getline(file, line))
    {
        tokenize(line); // your lexer
    }

    return 0;
}