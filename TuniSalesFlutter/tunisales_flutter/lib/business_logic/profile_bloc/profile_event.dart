part of 'profile_bloc.dart';

@immutable
abstract class ProfileEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class LoadProfile extends ProfileEvent {

  @override
  List<Object> get props => [];
}

class UpdateProfile extends ProfileEvent {

  final User user;

  UpdateProfile(this.user);

  @override
  List<Object> get props => [];
}
