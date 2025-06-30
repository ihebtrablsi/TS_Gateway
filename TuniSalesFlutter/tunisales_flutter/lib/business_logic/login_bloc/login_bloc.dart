import 'package:flutter/cupertino.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import '../../data/repository/login_repo.dart';
import 'login_event.dart';
import 'login_state.dart';

class LoginBloc extends Bloc<LoginEvent, LoginState> {
  final LoginRepo loginRepo;
  LoginBloc(this.loginRepo) : super(LoginInitial()) {
    on<LoginButtonPressed>((event, emit) async {
      emit(LoginLoading());

      try {
        String token =
            await loginRepo.getJWT(event.username, event.password, false);
        print(token);
        if (token != "Failed"){
          emit(LoginSuccess(succesMsg: 'Logged in Successfully as ${event.username}'));
        }else {
          emit(LoginFailure(error: 'Invalid username or password'));
        }
      } catch (error) {
        emit(LoginFailure(error: 'Invalid username or password'));
      }
    });
  }
}