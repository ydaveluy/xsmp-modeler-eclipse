package org.eclipse.xsmp;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.provider.FileSystem;
import org.eclipse.core.internal.filesystem.NullFileStore;
import org.eclipse.core.internal.filesystem.local.LocalFile;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

@SuppressWarnings("restriction")
public class XsmpBuiltinFileSystem extends FileSystem
{

  @Override
  public IFileStore getStore(URI uri)
  {
    try
    {
      final var url = FileLocator.toFileURL(getClass()
              .getResource("/node_modules/xsmp-modeler/out/lib/" + uri.getSchemeSpecificPart()));
      if (url != null)
      {
        return new LocalFile(new File(url.getFile()));
      }
    }
    catch (final IOException e)
    {
      // ignore
    }

    return new NullFileStore(new Path(uri.getPath()));
  }

}
