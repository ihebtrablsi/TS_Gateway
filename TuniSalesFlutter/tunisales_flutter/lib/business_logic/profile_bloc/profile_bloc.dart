import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:dio/dio.dart';
import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

import '../../data/models/user.dart';
import '../../data/repository/userRepo.dart';

part 'profile_event.dart';
part 'profile_state.dart';

class ProfileBloc extends Bloc<ProfileEvent, ProfileState> {

  final UserRepo _userRepo;

  ProfileBloc(this._userRepo) : super(ProfileInitial()) {
    on<LoadProfile>((event, emit) async {
      try {
        User user =
        await _userRepo.getUserAccount();
        print("ACCOUNT FROM BLOC");
        print(user);
        emit(ProfileSuccess(user: user));
      } catch (error) {
        emit(ProfileFailure(error: error.toString()));
      }
    });

    on<UpdateProfile>((event, emit) async {
      try {
        await _userRepo.upadateUser(event.user);
        // print("ACCOUNT FROM BLOC");
        // print(user);
        emit(ProfileUpdateSuccess(successMsg: "User Updated Successfully"));
      } catch (error) {
        // emit(ProfileUpdateFailure(error: error.toString()));

        if (error is DioError && error.response != null && error.response?.statusCode == 400) {
          // Handle 500 errors
          final Map<String, dynamic> errorData = error.response?.data ?? {};
          print('--------------------------------- Error: $errorData');
          final String detail = errorData['detail'] ?? 'Unknown error';
          // print('--------------------------------- Server error: ${detail}');
          emit(ProfileUpdateFailure(error: 'veuillez respecter le format du mail'.toUpperCase()));
        } else {
          // Handle other errors
          print('Error: $error');
          emit(ProfileUpdateFailure(error: '${error}. Try Again'));
        }

      }
    });
  }
}
