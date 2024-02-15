#include "vector.h"
#include <cmath>
#include <iostream>

namespace VECTOR {

void Vector::set_mag() {
    mag = sqrt(x * x + y * y);
}

void Vector::set_ang() {
    if (x == 0.0 && y == 0.0) {
        ang = 0;
    } else {
        ang = atan2(y, x);
    }
}

void Vector::set_x() {
    x = mag * cos(ang);
}

void Vector::set_y() {
    y = mag * sin(ang);
}

Vector::Vector(double num_1, double num_2, Mode type) {
    mode = type;

    if (type == RECT) {
        x = num_1;
        y = num_2;
        set_ang();
        set_mag();
    } else if (type == POL) {
        mag = num_1;
        ang = num_2;
        set_x();
        set_y();
    } else {
        std::cout << "ERROR: the 3rd argument in Vector() is incorrect.\n";
        std::cout << "Set Vector to 0";
        x = y = mag = ang = 0;
        mode = RECT;
    }
}

void Vector::reset(double num_1, double num_2, Mode type) {
    mode = type;

    if (type == RECT) {
        x = num_1;
        y = num_2;
        set_ang();
        set_mag();
    } else if (type == POL) {
        mag = num_1;
        ang = num_2;
        set_x();
        set_y();
    } else {
        std::cout << "ERROR: the 3rd argument in Vector() is incorrect.\n";
        std::cout << "Set Vector to 0";
        x = y = mag = ang = 0;
        mode = RECT;
    }
}

void Vector::polar_mode() {
    mode = POL;
}

void Vector::rect_mode() {
    mode = RECT;
}

// operator overload

Vector Vector::operator+(Vector& v) const {
    return Vector{x + v.x, y + v.y};
}

Vector Vector::operator-(Vector& v) const {
    return Vector{x - v.x, y - v.y};
}

Vector Vector::operator-() const {
    return Vector{-x, -y};
}

Vector Vector::operator*(double number) const {
    return Vector{number * x, number * y};
}

Vector operator*(double num, const Vector& v) {
    return v * num;
}

std::ostream& operator<<(std::ostream& os, const Vector& v) {
    using std::endl;

    if (v.mode == v.RECT) {
        os << v.x << " " << v.y << endl;
    } else {
        os << v.mag << " " << v.ang << endl;
    }

    return os;
}

}  // namespace VECTOR