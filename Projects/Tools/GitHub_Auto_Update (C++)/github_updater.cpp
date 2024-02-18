#include <cstring>
#include <iostream>

void command(char* msg) {
    if (system(msg)) {
        std::cout << "Error: fail to progress command " << msg << std::endl;
        system("pause");
        exit(EXIT_FAILURE);
    }
    std::cout << std::endl;
}

int main() {
    using namespace std;
    char msg[101];

    cout << "Enter message:";
    cin.getline(msg, 100);

    cout << "Working...\n";

    char msgs[100] = "git commit -m \"";
    strcat(msgs, msg);
    strcat(msgs, "\"");

    command("git add .");
    command(msgs);

    command("git push");

    cout << "Successfully update everything\n";
    system("pause");
    return 0;
}