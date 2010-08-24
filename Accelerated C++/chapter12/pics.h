#pragma once

#include <vector>

#include "Str.hpp"

Str::size_type width(const std::vector<Str>& v);

std::vector<Str> frame(const std::vector<Str>& v);

std::vector<Str> vcat(const std::vector<Str>& top,
		      const std::vector<Str>& bottom);

std::vector<Str> hcat(const std::vector<Str>& left,
		      const std::vector<Str>& right);
