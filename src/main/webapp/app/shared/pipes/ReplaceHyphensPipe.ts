import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'replaceHyphens' })
export class ReplaceHyphensPipe implements PipeTransform {
  transform(value: string): string {
    return value.replace(/-/g, ' ');
  }
}
