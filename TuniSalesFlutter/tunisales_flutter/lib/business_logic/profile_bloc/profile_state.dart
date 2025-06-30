part of 'profile_bloc.dart';

@immutable
abstract class ProfileState extends Equatable {
  @override
  List<Object> get props => [];
}

class ProfileInitial extends ProfileState {}

class ProfileLoading extends ProfileState {}

class ProfileSuccess extends ProfileState {
  final User user;

  ProfileSuccess({required this.user});

  @override
  List<Object> get props => [user];
}

class ProfileFailure extends ProfileState {
  final String error;

  ProfileFailure({required this.error});

  @override
  List<Object> get props => [error];
}

class ProfileUpdateSuccess extends ProfileState {
  final String successMsg;

  ProfileUpdateSuccess({required this.successMsg});

  @override
  List<Object> get props => [successMsg];
}

class ProfileUpdateFailure extends ProfileState {
  final String error;

  ProfileUpdateFailure({required this.error});

  @override
  List<Object> get props => [error];
}
