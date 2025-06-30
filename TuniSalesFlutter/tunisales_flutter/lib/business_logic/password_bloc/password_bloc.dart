import 'dart:async';

import 'package:bloc/bloc.dart';
import 'package:dio/dio.dart';
import 'package:equatable/equatable.dart';
import 'package:meta/meta.dart';

import '../../data/repository/userRepo.dart';

part 'password_event.dart';
part 'password_state.dart';

class PasswordBloc extends Bloc<PasswordEvent, PasswordState> {

  final UserRepo _userRepo;

  PasswordBloc(this._userRepo) : super(PasswordInitial()) {
    on<UpdatePassword>((event, emit) async {
      try {
        await _userRepo.upadatePassword(event.currentPassword,event.newPassword);
        // print("ACCOUNT FROM BLOC");
        // print(user);
        emit(PasswordUpdateSuccess(successMsg: "Password Updated Successfully"));
        // print("------------------------------- EMITTED");
        emit(PasswordInitial());

      } catch (error) {

        // emit(PasswordUpdateFailure(error: error.toString()));

        if (error is DioError && error.response != null && error.response?.statusCode == 400) {
          // Handle 500 errors
          final Map<String, dynamic> errorData = error.response?.data ?? {};
          // print('--------------------------------- Error: $errorData');
          final String detail = errorData['title'] ?? 'Unknown error';
          // print('--------------------------------- Server error: ${detail}');
          emit(PasswordUpdateFailure(error: detail.toUpperCase()));
        } else {
          // Handle other errors
          print('Error: $error');
          emit(PasswordUpdateFailure(error: '${error}. Try Again'));
        }
        emit(PasswordInitial());
      }
    });
  }
}
