#include <cstring>
#include <fstream>
#include <iostream>
#include <string>

#define MAX_LEN 1000

int is_title_one(char* target) {
    using std::strlen;

    int target_len = strlen(target);

    if (target_len == 0) {
        return 0;
    }

    int i = 0;
    for (i = 0; i < target_len; i++) {
        if (target[i] == '#') {
            continue;
        } else if (target[i] == ' ') {
            break;
        } else {
            return 0;
        }
    }
    return i;
}

int main() {
    using std::cin;
    using std::cout;
    using std::endl;
    using std::ifstream;
    using std::ofstream;
    using std::string;
    using std::system;
    using std::to_string;

    ifstream input_file;
    string path{};
    cout << "Please enter the path of the file:";
    cin >> path;
    input_file.open(path);
    if (input_file.is_open() == false) {
        cout << "ERROR: Fail to load file: " << path << endl;
        system("pause");
        exit(EXIT_FAILURE);
    }

    ofstream output_file;
    // int i = 0;

    string output_path{};
    cout << "Enter output path: ";
    cin >> output_path;

    string name = output_path + "intruduction.md";
    char buffer[MAX_LEN + 1]{};
    output_file.open(name);
    if (output_file.is_open() == false) {
        cout << "ERROE: Fail to load path: " << output_path << endl;
        system("pause");
        exit(EXIT_FAILURE);
    }

    cout << "Load seccess.\n";
    cout << "Analyzing...\n";
    while (!input_file.getline(buffer, MAX_LEN).fail()) {
        int title = is_title_one(buffer);
        if (title == 1) {
            output_file.close();
            break;
        } else if (title) {
            output_file << buffer + 1 << endl;
        } else {
            output_file << buffer << endl;
        }
    }

    while (input_file.fail() != true) {
        name = (string)(buffer + 1) + ".md";
        output_file.open(name);

        int title;
        while (1) {
            if (title) {
                output_file << buffer + 1 << endl;
            } else {
                output_file << buffer << endl;
            }
            input_file.getline(buffer, MAX_LEN + 1);
            title = is_title_one(buffer);
            if (title == 1 || input_file.fail()) {
                output_file.close();
                break;
            }
        }
    }

    cout << "Finished.\n";
    system("pause");
    return 0;
}
