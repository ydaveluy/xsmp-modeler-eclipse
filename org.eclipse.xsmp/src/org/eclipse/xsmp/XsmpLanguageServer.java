
package org.eclipse.xsmp;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.lsp4e.server.ProcessStreamConnectionProvider;
import org.eclipse.lsp4e.server.StreamConnectionProvider;
import org.eclipse.wildwebdeveloper.embedder.node.NodeJSManager;

/**
 * @author dhuebner
 */
public class XsmpLanguageServer extends ProcessStreamConnectionProvider
        implements StreamConnectionProvider
{

  public XsmpLanguageServer()
  {
    final List<String> commands = new ArrayList<>();
    commands.add(NodeJSManager.getNodeJsLocation().getAbsolutePath());
    try
    {
      final var url = FileLocator
              .toFileURL(getClass().getResource("/node_modules/xsmp-modeler/out/language/main.js"));
      commands.add(new java.io.File(url.getPath()).getAbsolutePath());
      commands.add("--stdio");
      setCommands(commands);
      setWorkingDirectory(System.getProperty("user.dir"));
    }
    catch (final IOException e)
    {
      Activator.getDefault().getLog().log(new Status(IStatus.ERROR,
              Activator.getDefault().getBundle().getSymbolicName(), e.getMessage(), e));
    }
  }

  @Override
  public Object getInitializationOptions(URI rootUri)
  {
    return Collections.singletonMap("provideFormatter", true);
  }

}
